import create from "zustand";
import axios from "axios";

interface answerState {
  postAnswer: (
    url: string,
    chatId: number,
    data: object,
    token: object
  ) => void;
}

export const answerStore = create<answerState>((set) => ({
  postAnswer: async (url, chatId, data, token) => {
    try {
      await axios.post(url + "/answer/" + chatId, data, {
        headers: token,
      });
    } catch (e) {
      console.log(e);
    }
  },
}));
