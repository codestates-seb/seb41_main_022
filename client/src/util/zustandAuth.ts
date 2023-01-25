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
        process.env.REACT_APP_API_URL + `/study/${studyId}/user/${userId}/auth`,
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
