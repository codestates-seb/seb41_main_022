import create from "zustand";
import axios from "axios";
interface joinStudyState {
  studyId: number | null;
  isLoading: boolean;
  fetchJoinStudy: (id: string | undefined, form: object, token: object) => void;
}
export const joinStudyStore = create<joinStudyState>((set) => ({
  studyId: null,
  isLoading: false,
  fetchJoinStudy: async (id, form, token) => {
    set({ isLoading: true });
    try {
      const response: any = await axios.post(
        process.env.REACT_APP_API_URL + `/study/${id}/requester`,
        form,
        {
          headers: token,
        }
      );
      set({ studyId: await response.data.data.studyId });
    } catch (error) {}
    set({ isLoading: false });
  },
}));
