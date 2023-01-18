import create from "zustand";
import axios from "axios";

interface Notice {
  notice: string;
  fetchNotice: (url: string, id: string | undefined) => void;
  patchNotice: (url: string, id: string | undefined, data: object) => void;
}

const NoticeStore = create<Notice>()((set) => ({
  notice: "",
  fetchNotice: async (url, id) => {
    try {
      const res = await axios.get(url + `/study/${id}/notification`);
      set({ notice: res.data.data.notice });
    } catch (e) {
      console.log(e);
    }
  },
  patchNotice: async (url, id, data) => {
    try {
      await axios
        .patch(url + `/study/${id}/notification`, data)
        .then((res) => console.log(res));
    } catch (e) {
      console.log(e);
    }
  },
}));
export default NoticeStore;
