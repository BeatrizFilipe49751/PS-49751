import { cvApi } from "./axiosInstances";
import { routes } from "./apiRoutes";
import { CvDTO, instanceOfCvDTO } from "../interfaces/models/CvDTO";

export const sendCvToCienciaVitae = () => cvApi.post(routes.cv.send);

export const importCv = (source: string, file: File) => {
  const formData = new FormData();
  formData.append("source", source);
  formData.append("file", file);
  return cvApi.post(routes.cv.import, formData);
};

/**
 * Strongly‑typed GET /cv
 *
 * @returns Promise<CvDTO>
 */
export const getCv = async (): Promise<CvDTO> => {
  const { data } = await cvApi.get<CvDTO>(routes.cv.get);

  if (!instanceOfCvDTO(data)) {
    throw new Error("Response is not a valid CvDTO");
  }

  return data;
};

export const putCv = (dto: CvDTO) => cvApi.put(routes.cv.update, dto);