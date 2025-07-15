export interface fieldType {
  formId: number;
  label: string;
  order: number;
  fieldId: number;
  fieldAnswersResponseDTO: {
    fieldsId: number;
    answers: string;
    id: number;
  };
}
