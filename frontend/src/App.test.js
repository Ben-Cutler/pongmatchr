import React from "react";
import App from "./App";
import { mount, shallow } from "enzyme";
import { addPlayer, getPlayers, getMatches } from "./apiHelper";
import { act } from "react-dom/test-utils";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";

jest.mock("./apiHelper");

describe("App", () => {
  beforeEach(() => {
    addPlayer.mockResolvedValue([]);
    getPlayers.mockResolvedValue([{id : 1,player: { name: "Ben" }}, {id:1 , player: { name: "Tom" }}]);
    getMatches.mockResolvedValue([])
  });

  afterEach(() => {
    addPlayer.mockReset();
    getPlayers.mockReset();
  })

  it("renders a prompt to enter user's name", () => {
    const subject = render();

    subject.find(TextField).simulate("change", { target: { value: "player1" } });
    subject.find(Button).simulate("click");
    expect(addPlayer).toHaveBeenCalledWith("player1");
  });

  it("gets all players upon first loading, Avatar is also a char :p", async () => {
    const subject = await deepRender();

    subject.update();
    expect(
      subject
        .find('[data-aid="player"]')
        .at(0)
        .text()
    ).toEqual("BBen");
    expect(
      subject
        .find('[data-aid="player"]')
        .at(1)
        .text()
    ).toEqual("TTom");
  });

  it("adds the users name to the list once it is successfully submitted", async () => {
    addPlayer.mockResolvedValue([{id:1, player:{name: "Elaine"}}]);

    const subject = render();

    subject.find(TextField).simulate("change", { target: { value: "Elaine" } });
    await subject.find(Button).simulate("click");

    expect(subject.find('[data-aid="player"]').at(0).text()).toEqual("EElaine");
  });

  it("Periodically updates users in the waiting room", async () => {
    jest.useFakeTimers();

    await deepRender();

    await act(async () => {
      await jest.advanceTimersByTime(11000);
    });
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

    return subject;
  };
});
