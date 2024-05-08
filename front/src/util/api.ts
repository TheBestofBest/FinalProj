import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8090",
  withCredentials: true,
});

export default api;
