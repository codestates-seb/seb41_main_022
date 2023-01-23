import create from "zustand";
import axios from "axios";

//타입지정
interface LoginState {
  authData: AuthData | undefined;
  checkAuth: (
    studyId: string,
    userId: number,
    accessToken: string,
    refreshToken: string
  ) => void;
}

interface AuthData {
  host: boolean;
  member: boolean;
  request: boolean;
}

const AuthStore = create<LoginState>((set) => ({
  authData: undefined,
  checkAuth: (studyId, userId, accessToken, refreshToken) => {
    axios
      .get(
        `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/${studyId}/user/${userId}/auth`,
        {
          headers: {
            "access-Token": accessToken,
            "refresh-Token": refreshToken,
          },
        }
      )
      .then((res) => set({ authData: res.data.data }));
  },
}));

export default AuthStore;
