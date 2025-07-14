import { cvApi } from "./axiosInstances";
import { routes } from "./apiRoutes";
import { CvDTO } from "../interfaces/models/CvDTO";

export const sendCvToCienciaVitae = () => cvApi.post(routes.cv.send);

export const importCv = (source: string, file: File) => {
  const formData = new FormData();
  formData.append("source", source);
  formData.append("file", file);
  return cvApi.post(routes.cv.import, formData);
};

export const getCv = () => cvApi.get(routes.cv.get);

export const putCv = (dto: CvDTO) => cvApi.put(routes.cv.update, dto);