import create from "zustand";
import axios from "axios";
interface createStudyState {
  studyId: number | null;
  isLoading: boolean;
  fetchCreateStudy: (url: string, form: object, token: object) => void;
  fetchEditStudy: (url: string, form: object, token: object) => void;
}
export const createStudyStore = create<createStudyState>((set) => ({
  studyId: null,
  isLoading: false,
  fetchCreateStudy: async (url, form, token) => {
    set({ isLoading: true });
    try {
      const response: any = await axios.post(url + "/study", form, {
        headers: token,
      });
      set({ studyId: await response.data.data.studyId });
    } catch (error) {
      console.log(error);
    }
    set({ isLoading: false });
  },
  fetchEditStudy: async (url, form, token) => {
    set({ isLoading: true });
    try {
      const response: any = await axios.patch(url + "/study", form, {
        headers: token,
      });
    } catch (error) {
      console.log(error);
    }
    set({ isLoading: false });
  },
}));
