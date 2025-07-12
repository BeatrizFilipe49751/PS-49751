const BASE_URL = "http://localhost:8081"
export const BASE_CV_URL = `${BASE_URL}/api/cv`;
export const BASE_USER_URL = `${BASE_URL}/api/users`;

export const routes = {
  user: {
    register: `${BASE_USER_URL}/register`,
    login: `${BASE_USER_URL}/login`,
    logout: `${BASE_USER_URL}/logout`
  },
  cv: {
    send: `${BASE_CV_URL}/send`,
    update: `${BASE_CV_URL}/update`,
    import: `${BASE_CV_URL}/import`,
    get: `${BASE_CV_URL}`
  }
};
