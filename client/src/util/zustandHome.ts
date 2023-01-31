import create from "zustand";
import axios from "axios";

interface Home {
  isLoading: boolean;
  page: number;
  tags: string;
  filter: string;
  search: string;
  recruitmentData: [] | Recruitment[];
  totalPage: number;
  fetch: (tags: string, filter: string, search: string, page: number) => void;
  setTags: (tag: string) => void;
  setFilter: (filter: string) => void;
  setSearch: (search: string) => void;
  setRecruitment: (data: Recruitment[]) => void;
  setPage: (data: number) => void;
}

interface Recruitment {
  content: string;
  dayOfWeek: string[];
  image: string;
  leaderId: number;
  notice?: null;
  openClose: boolean;
  onOff: boolean;
  requester: [];
  startDate: string;
  studyId: number;
  summary: string;
  teamName: string;
  want: number;
}

const HomeStore = create<Home>()((set) => ({
  isLoading: false,
  tags: "",
  filter: "",
  search: "",
  page: 1,
  recruitmentData: [],
  totalPage: 1,
  setRecruitment: (data: Recruitment[] | []) => {
    set(() => ({ recruitmentData: data }));
  },
  setPage: (data: number) => {
    set(() => ({ page: data }));
  },
  fetch: async (tags, filter, search, page) => {
    set(() => ({
      isLoading: true,
    }));
    try {
      const res = await axios.get(
        process.env.REACT_APP_API_URL +
          `/study/cards?page=${page}&size=12&search=${search}&filter=${filter}&tags=${tags}`
      );

      setTimeout(() => {
        set((state) => ({
          recruitmentData: [...state.recruitmentData, ...res.data.data],
          totalPage: res.data.pageInfo.totalPages,
        }));
        set(() => ({
          isLoading: false,
        }));
      }, 500);
    } catch (err) {}
  },
  setTags: (tag: string) => {
    set(() => ({ page: 1, tags: tag, recruitmentData: [] }));
  },
  setFilter: (filter: string) => {
    set(() => ({ page: 1, filter: filter, recruitmentData: [] }));
  },
  setSearch: (search: string) => {
    set(() => ({ page: 1, search: search, recruitmentData: [] }));
  },
}));

export default HomeStore;
