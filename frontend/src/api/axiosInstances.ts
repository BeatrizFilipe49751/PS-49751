import axios from "axios";
import { BASE_CV_URL, BASE_USER_URL } from "./apiRoutes";

// For CV endpoints (token required)
export const cvApi = axios.create({
  baseURL: BASE_CV_URL
});

cvApi.interceptors.request.use(config => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// For User endpoints (token NOT required by default)
export const userApi = axios.create({
  baseURL: BASE_USER_URL
});
