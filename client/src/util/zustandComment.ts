import create from "zustand";
import axios from "axios";

interface commentState {
  postComment: (url: string, id: string, data: object, token: string) => void;
}

export const commentStore = create<commentState>((set) => ({
  postComment: async (url, id, data, token) => {
    try {
      await axios.post(url + "/chat?studyId=" + id, data, {
        headers: {
          "access-Token": token,
        },
      });
    } catch (e) {
      console.log(e);
    }
  },
}));
