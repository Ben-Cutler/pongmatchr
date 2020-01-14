export const addPlayer = (playerName) => {
  window.fetch('/api/players', {
    method : 'POST',
    headers:{
      'Content-type' : 'application/json'
    },
    body : JSON.stringify({'name' : playerName}),
  })
};

export const getPlayers = async () => {
  const response = await window.fetch('/api/players', {
    method : 'GET',
  });
  return await response.json()
};