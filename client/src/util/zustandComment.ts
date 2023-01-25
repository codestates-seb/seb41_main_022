import create from "zustand";
import axios from "axios";

//타입지정
interface CommentState {
  postComment: (id: string, data: object, token: object) => void;
}

export const commentStore = create<CommentState>((set) => ({
  postComment: async (id, data, token) => {
    try {
      await axios.post(
        process.env.REACT_APP_API_URL + "/chat?studyId=" + id,
        data,
        {
          headers: token,
        }
      );
    } catch (e) {
      console.log(e);
    }
  },
}));
