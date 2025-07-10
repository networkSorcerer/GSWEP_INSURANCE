// GoogleLoginButton.tsx
import {
  GoogleOAuthProvider,
  GoogleLogin,
  type CredentialResponse,
} from "@react-oauth/google";
import OAuth2Button from "./OAuth2Button";

interface GoogleLoginButtonProps {
  onSuccess: (response: CredentialResponse) => void;
  onError: () => void;
}

const GoogleLoginButton: React.FC<GoogleLoginButtonProps> = ({
  onSuccess,
  onError,
}) => {
  return (
    <GoogleOAuthProvider clientId="711497532760-gt04q7avm9nqsr0ocmjrbnap2vnk8r4i.apps.googleusercontent.com">
      <GoogleLogin
        onSuccess={onSuccess}
        onError={onError}
        theme="filled_blue"
        width="100%"
        render={(renderProps) => (
          <OAuth2Button
            onClick={renderProps.onClick}
            style={{ backgroundColor: "#4285F4" }}
          >
            구글 로그인
          </OAuth2Button>
        )}
      />
    </GoogleOAuthProvider>
  );
};

export default GoogleLoginButton;
