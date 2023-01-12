//타입지정
interface AnswerProps {
  answerUserId: string;
  content: string;
}

const Answer = ({ answerUserId, content }: AnswerProps) => {
  return <div>Answer</div>;
};

export default Answer;
