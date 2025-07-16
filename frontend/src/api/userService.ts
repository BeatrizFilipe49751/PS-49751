import { userApi } from "./axiosInstances";
import { routes } from "./apiRoutes";

export const registerUser = (data: {
  email: string;
  password: string;
  cienciaID: string;
}) => userApi.post(routes.user.register, data);

export const loginUser = (data: {
  email: string;
  password: string;
}) => userApi.post(routes.user.login, data);

export const logoutUser = () => {
  const token = localStorage.getItem("token");
  return userApi.post(routes.user.logout, null, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};
