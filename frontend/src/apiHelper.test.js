import {addPlayer, getPlayers} from "./apiHelper";

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

  it('gets all players waiting to play with the getall endpoint', async () => {
    global.fetch = jest.fn();
    const players = [{name: "bob"}, {name: "sue"}];
    global.fetch.mockResolvedValue({json: async () => players});


    const result = await getPlayers();

    expect(window.fetch).toHaveBeenCalledWith("/api/players", {
      method: "GET",
    });

    expect(result).toEqual(players);
  })
});