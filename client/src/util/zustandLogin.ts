import create from "zustand";

//타입지정
interface LoginState {
  isLogin: boolean;
  setIsLogin: (state: boolean) => void;
}

const LoginStore = create<LoginState>((set) => ({
  isLogin: false,
  setIsLogin: (state) => set({ isLogin: state }),
}));

export default LoginStore;
