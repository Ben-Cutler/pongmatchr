export const addPlayer = async (playerName) => {
  const response = await window.fetch('/api/players', {
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
  const response = await window.fetch('/api/players', {
    method : 'GET',
  });
  return await response.json()
};