import create from "zustand";
import axios from "axios";

//타입지정
interface DropState {
  dropdownGet: (token: object) => void;
  dropdownData: DataType[] | undefined;
}
interface DataType {
  studyId: string;
  teamName: string;
}

const DropdownStore = create<DropState>((set) => ({
  dropdownData: undefined,
  dropdownGet: (token) => {
    axios
      .get(process.env.REACT_APP_API_URL + "/study/user", {
        headers: token,
      })
      .then((res) => {
        set({ dropdownData: res.data.data.studies });
      });
  },
}));

export default DropdownStore;
