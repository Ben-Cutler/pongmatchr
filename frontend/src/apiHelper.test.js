import {addPlayer} from "./apiHelper";

describe("apiHelper", () => {
  it("addPlayer does a POST to /api/players", () => {
    global.fetch = jest.fn();

    addPlayer("player1");

    expect(window.fetch).toHaveBeenCalledWith("/api/players", {
      method: "POST",
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify({name: "player1"})
    });
  });
});