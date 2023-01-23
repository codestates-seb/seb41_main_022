import create from "zustand";
import axios from "axios";
interface joinStudyState {
  studyId: number | null;
  isLoading: boolean;
  fetchJoinStudy: (
    url: string,
    id: string | undefined,
    form: object,
    token: object
  ) => void;
}
export const joinStudyStore = create<joinStudyState>((set) => ({
  studyId: null,
  isLoading: false,
  fetchJoinStudy: async (url, id, form, token) => {
    set({ isLoading: true });
    try {
      const response: any = await axios.post(
        url + `/study/${id}/requester`,
        form,
        {
          headers: token,
        }
      );
      set({ studyId: await response.data.data.studyId });
      alert("가입신청 되었습니다");
    } catch (error) {
      console.log(error);
      alert("에러");
    }
    set({ isLoading: false });
  },
}));
