# CS3219-G16-Frontend

[![Netlify Status](https://api.netlify.com/api/v1/badges/f2e9a54e-5ad3-474d-b77e-fc2977f8102f/deploy-status)](https://app.netlify.com/sites/peerprep-g16/deploys)

Deployed domian: https://peerprep-g16.netlify.app/

## Steps to setup the frontend:
#### Using NPM:
In the frontend folder, run:
- `npm install`
- `npm start`

#### Using Yarn:
In the frontend folder, run:
- `yarn install`
- `yarn start`

## Steps to toggle between local and production environments:
### For Restful APIs
- Open `src/apiHandler` folder
- Toggle the comment at line 4 and 5 of each file in the folder to choose the URL to the corresponding backend server

To use the **remote** backend sever, please edit **line 3-6** of **each file in the folder** as below: 
```
const client = axios.create({
  // baseURL: "http://localhost:8080/api/v1",
  baseURL: "https://peerprep-backend-service-mepxozmoha-as.a.run.app/api/v1"
});
```

To use the **local** backend sever, please edit **line 3-6** of **each file in the folder** as below: 
```
const client = axios.create({
  baseURL: "http://localhost:8080/api/v1",
  // baseURL: "https://peerprep-backend-service-mepxozmoha-as.a.run.app/api/v1"
});
```
### For WebSocket API
- Open `src/view/pages/authenticated/InterviewDashboard`
- Toggle the comment at line 36 and 37 choose the URL to the corresponding backend server

To use the **remote** backend sever:
```
  const [editingUrl, setEditingUrl] = useState("wss://peerprep-backend-service-mepxozmoha-as.a.run.app/interview/")
  // const [editingUrl, setEditingUrl] = useState("ws://localhost:8080/interview/")
```

To use the **local** backend sever:
```
  // const [editingUrl, setEditingUrl] = useState("wss://peerprep-backend-service-mepxozmoha-as.a.run.app/interview/")
  const [editingUrl, setEditingUrl] = useState("ws://localhost:8080/interview/")
```


