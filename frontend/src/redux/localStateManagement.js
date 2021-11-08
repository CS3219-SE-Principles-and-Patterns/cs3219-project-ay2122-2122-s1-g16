export const loadState = (stateKey) => {
    try {
      var serializedState = localStorage.getItem(stateKey);
      if (serializedState === undefined || serializedState === null) {
        return null;
      }
      console.log(serializedState)
      return JSON.parse(serializedState);
    } catch (err) {
      console.log(err)
      return null;
    }
  };
  
export const saveState = (stateKey, stateValue) => {
  try {
    if (stateValue === null) {
      localStorage.removeItem(stateKey);
    } else {
      var serializedState = JSON.stringify(stateValue);
      localStorage.setItem(stateKey, serializedState);
    }
  } catch (err) {
    console.log(err)
  }
};

export const removeState = (stateKey) => localStorage.removeItem(stateKey);