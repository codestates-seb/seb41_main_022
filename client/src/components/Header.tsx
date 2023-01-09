import axios from "axios";

const Header = () => {
  axios
    .get("ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/message/1")
    .then((res) => console.log(res.data));

  return <div></div>;
};

export default Header;
