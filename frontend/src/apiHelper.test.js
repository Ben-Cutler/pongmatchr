import {addPlayer, getPlayers} from "./apiHelper";

describe("apiHelper", () => {
  it("addPlayer does a POST to /api/players", async () => {
    global.fetch = jest.fn();
    const players = [];
    global.fetch.mockResolvedValue({json: async () => players, status: 200});

    const result = await addPlayer("player1");

    expect(window.fetch).toHaveBeenCalledWith("/api/players", {
      method: "POST",
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify({name: "player1"})
    });

    expect(result).toEqual(players);
  });

  it("addPlayer throws an exception", async () => {
    global.fetch = jest.fn();
    const players = [];
    global.fetch.mockResolvedValue({status: 500});
    try {
      await addPlayer("player1");
      expect("Didn't").toEqual("expect to get here");
    } catch (e) {
      expect(e).toEqual("POST failed - received status: 500");
    }
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