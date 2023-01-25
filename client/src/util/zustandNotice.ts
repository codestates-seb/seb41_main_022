import create from "zustand";
import axios from "axios";

interface Notice {
  dayOfWeek: string[];
  notice: string;
  resetNotice: () => void;
  saveNotice: boolean;
  setSaveNotice: () => void;
  zustandStudyId: string;
  fetchRightNav: (id: string | undefined) => void;
  patchNotice: (id: string | undefined, data: object) => void;
}

const NoticeStore = create<Notice>()((set) => ({
  dayOfWeek: [""],
  notice: "",
  resetNotice: () => set({ notice: "" }),
  saveNotice: false,
  setSaveNotice: () => set((state) => ({ saveNotice: !state.saveNotice })),
  zustandStudyId: "",
  fetchRightNav: async (id) => {
    try {
      const res = await axios.get(
        process.env.REACT_APP_API_URL + `/study/${id}/notice`
      );
      set({ notice: res.data.data.notice });
      set({ zustandStudyId: id });
      set({ dayOfWeek: res.data.data.dayOfWeek });
    } catch (e) {
      console.log(e);
    }
  },
  patchNotice: async (id, data) => {
    try {
      await axios
        .patch(
          process.env.REACT_APP_API_URL + `/study/${id}/notification`,
          data
        )
        .then((res) => console.log(res));
    } catch (e) {
      console.log(e);
    }
  },
}));
export default NoticeStore;
