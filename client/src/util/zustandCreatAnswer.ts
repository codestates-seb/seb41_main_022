import create from "zustand";
import axios from "axios";

interface AnswerState {
  postAnswer: (chatId: number, data: object, token: object) => void;
}

export const answerStore = create<AnswerState>((set) => ({
  postAnswer: async (chatId, data, token) => {
    try {
      await axios.post(
        process.env.REACT_APP_API_URL + "/answer/" + chatId,
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
