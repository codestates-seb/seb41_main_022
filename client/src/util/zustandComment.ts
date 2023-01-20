import create from "zustand";
import axios from "axios";

//타입지정
interface commentState {
  postComment: (url: string, id: string, data: object, token: string) => void;
}
//                   함수이름   =      지정한 타입들 이거야~((set:그냥하는거)=>{
// 코멘트보내기(이름): async (url, id, data, token) 보낼거야 =>어디로? {
//   try(동작할칭구들) {
//    await axios.post(보낼꺼야 서버에 신호를...)(url, obj, data순으로 작성{
// headers: {
// '받게되는 토큰' : 토큰
//},
//});
//} catch(안써주면 클라이언트들에게 에러코드 보임) (e) {
//console.log(e)
//}
//},
//}));
export const commentStore = create<commentState>((set) => ({
  postComment: async (url, id, data, token) => {
    try {
      await axios.post(url + "/chat?studyId=" + id, data, {
        headers: {
          "access-Token": token,
        },
      });
    } catch (e) {
      console.log(e);
    }
  },
}));
