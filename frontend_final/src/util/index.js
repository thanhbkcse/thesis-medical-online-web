export const onlyNumber = e => {
  let keyCode = e.keyCode ? e.keyCode : e.which;
  if (
    (keyCode < 48 || keyCode > 57) &&
    (keyCode < 96 || keyCode > 105) &&
    keyCode != 8
  ) {
    e.preventDefault();
  }
};
