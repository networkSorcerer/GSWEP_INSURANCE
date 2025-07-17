import { useState } from "react";

interface FormField {
  id: number;
  label: string;
  value: string;
}

const initialFields: FormField[] = [
  { id: 1, label: "이름", value: "" },
  { id: 2, label: "이메일", value: "" },
];

const DynamicForm = () => {
  const [fields, setFields] = useState<FormField[]>(initialFields);

  const handleChange = (id: number, newValue: string) => {
    setFields((prev) =>
      prev.map((field) =>
        field.id === id ? { ...field, value: newValue } : field
      )
    );
  };

  const handleAddField = () => {
    const nextId = fields.length + 1;
    setFields((prev) => [
      ...prev,
      {
        id: nextId,
        label: `추가항목 ${nextId}`,
        value: "",
      },
    ]);
  };

  return (
    <div>
      <h2>동적 폼</h2>
      {fields.map((field) => (
        <div key={field.id}>
          <label htmlFor={`field-${field.id}`}>{field.label}</label>
          <input
            id={`field-${field.id}`}
            type="text"
            value={field.value}
            onChange={(e) => handleChange(field.id, e.target.value)}
          />
        </div>
      ))}
      <button onClick={handleAddField}>입력 항목 추가</button>
    </div>
  );
};

export default DynamicForm;
