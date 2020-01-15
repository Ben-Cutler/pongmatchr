import React from "react";
import App from "./App";
import { mount, shallow } from "enzyme";
import { addPlayer, getPlayers } from "./apiHelper";
import { act } from "react-dom/test-utils";

jest.mock("./apiHelper");

describe("App", () => {
  beforeEach(() => {
    addPlayer.mockResolvedValue({status: 200});
    getPlayers.mockResolvedValue([{ name: "Ben" }, { name: "Tom" }]);
  });

  it("renders a prompt to enter user's name", () => {
    const subject = render();

    subject.find("input").simulate("change", { target: { value: "player1" } });
    subject.find("button").simulate("click");
    expect(addPlayer).toHaveBeenCalledWith("player1");
  });

  it("gets all players upon first loading", async () => {
    const subject = deepRender();

    subject.update();
    expect(
      subject
        .find('[data-aid="player"]')
        .at(0)
        .text()
    ).toEqual("Ben");
    expect(
      subject
        .find('[data-aid="player"]')
        .at(1)
        .text()
    ).toEqual("Tom");
  });

  it("adds the users name to the list once it is successfully submitted", async () => {
    const subject = render();

    subject.find("input").simulate("change", { target: { value: "Elaine" } });
    await subject.find("button").simulate("click");

    expect(subject.find('[data-aid="player"]').at(0).text()).toEqual("Elaine");
  });

  it("Periodically updates users in the waiting room", async () => {
    jest.useFakeTimers();
    const subject = deepRender()

    jest.advanceTimersByTime(11000);
    expect(getPlayers).toHaveBeenCalledTimes(3)
  });

  const render = () => {
    return shallow(<App />);
  };

  const deepRender = async () => {
    const subject = mount(<App />);
    await act(async () => {
      await subject.update();
    });
    return subject
  };
});
