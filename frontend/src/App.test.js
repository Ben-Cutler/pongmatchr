import React from 'react';
import App from './App';
import {mount, shallow} from "enzyme";
import {addPlayer, getPlayers} from "./apiHelper";
import {act} from "react-dom/test-utils";

jest.mock("./apiHelper");

describe("App", () => {
  it("renders a prompt to enter user's name", () => {
    const subject = render();

    subject.find('input').simulate("change", {target: {value: "player1"}});
    subject.find('button').simulate("click");
    expect(addPlayer).toHaveBeenCalledWith("player1");
  });

  it("gets all players upon first loading", async () => {
    getPlayers.mockResolvedValue([
      {name: "Ben"},
      {name: "Tom"}
    ]);

    const subject = deepRender();

    await act(async () => {
      await subject.update();
    });

    subject.update();
    expect(subject.find('[data-aid="player"]').at(0).text()).toEqual("Ben");
    expect(subject.find('[data-aid="player"]').at(1).text()).toEqual("Tom");
  });

  const render = () => {
    return shallow(<App />)
  };

  const deepRender = () => {
    return mount(<App />)
  };
});