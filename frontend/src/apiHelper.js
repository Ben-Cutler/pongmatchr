export const addPlayer = async (playerName) => {
  const response = await window.fetch('/api/waiting_room_entries', {
    method : 'POST',
    headers:{
      'Content-type' : 'application/json'
    },
    body : JSON.stringify({'name' : playerName}),
  });

  if (response.status === 200) {
    return await response.json()
  } else {
    throw `POST failed - received status: ${response.status}`
  }
};

export const getPlayers = async () => {
  const response = await window.fetch('/api/waiting_room_entries', {
    method : 'GET',
  });
  return await response.json()
};

export const getMatches = async () => {
  const response = await window.fetch('/api/matches', {
    method : 'GET',
  });
  return await response.json()
};

export const updateMatch = async(id, match) => {
  const response = await window.fetch("/api/matches/" + id , {
    method : "PATCH",
    headers:{
      'Content-type' : 'application/json'
    },
    body : JSON.stringify(match),
  });
  return await response.json()
};