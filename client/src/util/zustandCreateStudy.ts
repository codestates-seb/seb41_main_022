import create from "zustand";
import axios from "axios";
interface createStudyState {
  studyId: number | null;
  isLoading: boolean;
  fetchCreateStudy: (form: object, token: object) => void;
  fetchEditStudy: (
    form: object,
    token: object,
    studyId: string | undefined
  ) => void;
}
export const createStudyStore = create<createStudyState>((set) => ({
  studyId: null,
  isLoading: false,
  fetchCreateStudy: async (form, token) => {
    set({ isLoading: true });
    try {
      const response: any = await axios.post(
        process.env.REACT_APP_API_URL + "/study",
        form,
        {
          headers: token,
        }
      );
      set({ studyId: await response.data.data.studyId });
    } catch (error) {
      console.log(error);
    }
    set({ isLoading: false });
  },
  fetchEditStudy: async (form, token, studyId) => {
    set({ isLoading: true });
    try {
      const response: any = await axios.patch(
        process.env.REACT_APP_API_URL + `/study/${studyId}/main`,
        form,
        {
          headers: token,
        }
      );
    } catch (error) {
      console.log(error);
    }
    set({ isLoading: false });
  },
}));
