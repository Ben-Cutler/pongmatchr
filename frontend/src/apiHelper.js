export const addPlayer = (playerName) => {
  window.fetch('/api/players', {
    method : 'POST',
    headers:{
      'Content-type' : 'application/json'
    },
    body : JSON.stringify({'name' : playerName}),
  })
};