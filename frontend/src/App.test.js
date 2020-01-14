import React from 'react';
import App from './App';
import {shallow} from "enzyme";
import {addPlayer} from "./apiHelper";

jest.mock("./apiHelper");

describe("App", () => {
  it("renders a prompt to enter user's name", () => {
    const subject = render();
    // expect(subject.find('input')).toHaveLength(1);
    subject.find('input').simulate("change", {target: {value: "player1"}});
    subject.find('button').simulate("click");
    expect(addPlayer).toHaveBeenCalledWith("player1");
  });

  render = () => {
    return shallow(<App />)
  }
});