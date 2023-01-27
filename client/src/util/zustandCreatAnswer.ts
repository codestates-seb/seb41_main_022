import create from "zustand";
import axios from "axios";

interface AnswerState {
  postedAnswer: any;
  postAnswer: (chatId: number, data: object, token: object) => void;
}

export const answerStore = create<AnswerState>((set) => ({
  postedAnswer: undefined,
  postAnswer: async (chatId, data, token) => {
    try {
      await axios
        .post(process.env.REACT_APP_API_URL + "/answer/" + chatId, data, {
          headers: token,
        })
        .then((res) => {
          set({ postedAnswer: res.data.data });
        });
    } catch (e) {
      console.log(e);
    }
  },
}));
