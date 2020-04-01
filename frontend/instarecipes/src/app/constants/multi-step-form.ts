const DATA_STEP_1 = {
  username: {
    type: 'text',
    validations: { required: true},
    errors: { required: "Username is required"},
    placeholder: 'Username'
  },
  email: {
    type: 'text',
    validations: { required: true },
    errors: {required: "Email is required"},
    placeholder: 'Email' },

  password: {
    type: 'password',
    validations: { required: true },
    errors: {required: "Password is required"},
    placeholder: 'Password'
  },
  confPassword: {
    type: 'password',
    validations: { required: true },
    errors: {required: "Confirm password is required"},
    placeholder: 'Confirm Password'
  }
};

const DATA_STEP_2 = {
  name: {
    type: 'text',
    validations: { required: true },
    errors: { required: 'Name is required' },
    placeholder: 'Name' },
  surname: {
    type: 'text',
    validations: { required: true },
    errors: { required: 'Surname is required' },
    placeholder: 'Surname' },
};

const STEPS = [
  { label: '', data: DATA_STEP_1 },
  { label: '', data: DATA_STEP_2 },
  { label: 'Review & Submit', data: {} }
];

export { STEPS }
