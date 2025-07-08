import { createContext, useState, type FC } from "react";

interface Context {
  searchKeyword: object;
  setSearchKeyword: (keyword: object) => void;
}

const defaultValue: Context = {
  searchKeyword: {},
  setSearchKeyword: () => {},
};

export const ContractContext = createContext(defaultValue);

export const ContractProvider: FC<{
  children: React.ReactNode | React.ReactNode[];
}> = ({ children }) => {
  const [searchKeyword, setSearchKeyword] = useState({});
  return (
    <ContractContext.Provider value={{ searchKeyword, setSearchKeyword }}>
      {children}
    </ContractContext.Provider>
  );
};
