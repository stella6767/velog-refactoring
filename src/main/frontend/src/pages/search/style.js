import styled from 'styled-components';

export const StyledTagSearchedDiv = styled.div`
  display: flex;
  height: 4rem;
  position: relative;
`;

export const StyledSearchDiv = styled.div`
  display: flex;
  height: 4rem;
  position: relative;
  border: solid 1px black;

  .search-Input {
    padding-left: 3rem;
    border: none;
    margin-left: 3rem;
    font-size: 1.5rem;
  }

  input::placeholder {
    opacity: 1;
    font-size: 1.3rem;
  }
`;

export const StyledSearchContainerDiv = styled.div`
  /* padding: 2rem; */
  padding-left: 3rem;
  padding-right: 3rem;
  padding-top: 2rem;
  width: 768px;
  margin-left: auto;
  margin-right: auto;
`;
