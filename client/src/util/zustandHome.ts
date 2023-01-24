import create from "zustand";
import axios from "axios";

interface Home {
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
  procedure: boolean;
  requester: [];
  startDate: string;
  studyId: number;
  summary: string;
  teamName: string;
  want: number;
}

const HomeStore = create<Home>()((set) => ({
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
    try {
      const res = await axios.get(
        `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/cards?page=${page}&size=12&search=${search}&filter=${filter}&tags=${tags}`
      );
      set((state) => ({
        recruitmentData: [...state.recruitmentData, ...res.data.data],
        totalPage: res.data.pageInfo.totalPages,
      }));
      console.log(res.data);
      console.log(tags, page);
    } catch (e) {
      console.log(e);
    }
  },
  setTags: (tag: string) => {
    set(() => ({ page: 1, tags: tag, recruitmentData: [] }));
  },
  setFilter: (filter: string) => {
    set(() => ({ page: 1, filter: filter }));
  },
  setSearch: (search: string) => {
    set(() => ({ page: 1, search: search }));
  },
}));

export default HomeStore;
