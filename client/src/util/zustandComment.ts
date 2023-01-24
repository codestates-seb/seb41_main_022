import create from "zustand";
import axios from "axios";

//타입지정
interface CommentState {
  postComment: (url: string, id: string, data: object, token: object) => void;
}

export const commentStore = create<CommentState>((set) => ({
  postComment: async (url, id, data, token) => {
    try {
      await axios.post(url + "/chat?studyId=" + id, data, {
        headers: token,
      });
    } catch (e) {
      console.log(e);
    }
  },
}));
