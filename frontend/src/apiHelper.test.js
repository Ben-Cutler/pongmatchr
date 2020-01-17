import {addPlayer, getMatches, getPlayers, updateMatch} from "./apiHelper";

describe("apiHelper", () => {
  it("addPlayer does a POST to /api/players", async () => {
    global.fetch = jest.fn();
    const players = [];
    global.fetch.mockResolvedValue({json: async () => players, status: 200});

    const result = await addPlayer("player1");

    expect(window.fetch).toHaveBeenCalledWith("/api/waiting_room_entries", {
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

    expect(window.fetch).toHaveBeenCalledWith("/api/waiting_room_entries", {
      method: "GET",
    });

    expect(result).toEqual(players);
  });

  it('gets all matches', async () => {
    global.fetch = jest.fn();
    const matches = [];
    global.fetch.mockResolvedValue({json: async () => matches});


    const result = await getMatches();

    expect(window.fetch).toHaveBeenCalledWith("/api/matches", {
      method: "GET",
    });

    expect(result).toEqual(matches);
  });

  it('updates an existing match', async () => {
    global.fetch = jest.fn();
    const match = {id: 123, winner: {id: 1, name: "bob"}};
    global.fetch.mockResolvedValue({json: async () => match, status: 200});

    const result = await updateMatch(123, match);

    expect(window.fetch).toHaveBeenCalledWith("/api/matches/123", {
      method: "PATCH",
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(match)
    });

    expect(result).toEqual(match);
  });
});