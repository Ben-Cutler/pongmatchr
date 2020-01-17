import {Matches} from "./Matches";
import {mount} from "enzyme";
import React from "react";
import {act} from "react-dom/test-utils";
import {getMatches} from "./apiHelper";

jest.mock('./apiHelper')

describe('Matches', function () {
  it('displays all matches', async () => {
    getMatches.mockResolvedValue([{id:1,
      player1 : {name:"Tom", id:1},
      player2: {name:"Ben", id:2}
    }])
    const subject = await deepRender();
    subject.update();

    expect(subject.find("[data-aid='App__match']").text()).toContain("Tom + Ben")
  });

  const deepRender = async () => {
    const subject = mount(<Matches />);

    await act(async () => {
      await subject.update();
    });

    return subject;
  };
});