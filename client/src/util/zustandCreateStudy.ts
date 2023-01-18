import create from "zustand";
import axios from "axios";
interface createStudyState {
  studyId: number | null;
  fetchCreateStudy: (url: string, form: object) => void;
}
export const createStudyStore = create<createStudyState>((set) => ({
  studyId: null,
  fetchCreateStudy: async (url, form) => {
    try {
      const response: any = await axios.post(url + "/study", form, {
        headers: {
          "access-Token": "abcd",
        },
      });
      set({ studyId: await response.data.data.studyId });
    } catch (error) {
      console.log(error);
    }
  },
}));
