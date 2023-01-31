import create from "zustand";
import axios from "axios";

//타입지정

interface Studies {
  chatId: any;
  username: string;
  content: string;
  answers: [];
  pageInfo: any;
  totalElements: number;
  size: number;
  imgUrl: string;
  isClosedChat: boolean;
  chatCreatedAt: string;
}
interface PageInfo {
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

interface GroupType {
  data: Studies[];
  pageInfo: PageInfo;
}

interface CommentState {
  postComment: (id: string, data: object, token: object) => void;
  fetchCommentData: (
    accessToken: string,
    refreshToken: string,
    studyId: string,
    page: number,
    size: number
  ) => void;
  commentsData: GroupType | undefined;
}

export const commentStore = create<CommentState>((set) => ({
  commentsData: undefined,
  fetchCommentData(accessToken, refreshToken, studyId, page, size) {
    axios
      .get(
        process.env.REACT_APP_API_URL +
          `/chat/${studyId}?page=${page}&size=${size}`,
        {
          headers: {
            "access-Token": accessToken,
            "refresh-Token": refreshToken,
          },
        }
      )
      .then((res) => set({ commentsData: res.data }));
  },
  postComment: async (id, data, token) => {
    try {
      await axios.post(
        process.env.REACT_APP_API_URL + "/chat?studyId=" + id,
        data,
        {
          headers: token,
        }
      );
    } catch (e) {}
  },
}));
