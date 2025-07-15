import { useState } from "react";

const MultiInput = () => {
  const [val, setVal] = useState({ userName: "", nickName: "" });

  // e: React.ChangeEvent<HTMLInputElement> 타입으로 받기
  const chkVal = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    console.log(name, value);

    // 상태 업데이트 - 이름에 따라 userName 또는 nickName 변경
    setVal((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const resetVal = () => {
    setVal({ userName: "", nickName: "" });
  };

  return (
    <div>
      <input
        name="userName"
        placeholder="이름"
        value={val.userName}
        onChange={chkVal}
      />
      <input
        name="nickName"
        placeholder="별명"
        value={val.nickName}
        onChange={chkVal}
      />
      <button onClick={resetVal}>초기화</button>

      <div>
        입력값: {val.userName} / {val.nickName}
      </div>
    </div>
  );
};

export default MultiInput;
  