import create from "zustand";
import axios from "axios";

interface Community {
  chatData: ChatData[];
  getChatData: (studyId: string) => void;
  submitChat: (
    studyId: string,
    content: string,
    dateTime: string,
    accessToken: string,
    refreshToken: string
  ) => void;
}

interface ChatData {
  content: string;
  dateTime: string;
  messageUserId: number;
  username: string;
  imgUrl: string;
}

const ChatStore = create<Community>()((set) => ({
  chatData: [],
  getChatData: async (studyId) => {
    try {
      const res = await axios.get(
        process.env.REACT_APP_API_URL + `/message?studyId=${studyId}`
      );
      set({ chatData: await res.data.data });
    } catch (e) {}
  },
  submitChat: async (studyId, content, dateTime, accessToken, refreshToken) => {
    try {
      const res = await axios.post(
        process.env.REACT_APP_API_URL + `/message?studyId=${studyId}`,
        {
          content: content,
          dateTime: dateTime,
        },
        {
          headers: {
            "access-Token": accessToken,
            "refresh-Token": refreshToken,
          },
        }
      );
      set((state) => ({ chatData: [...state.chatData, res.data.data] }));
    } catch (e) {}
  },
}));

export default ChatStore;
