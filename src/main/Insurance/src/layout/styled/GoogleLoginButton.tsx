// GoogleLoginButton.tsx
import {
  GoogleOAuthProvider,
  GoogleLogin,
  type CredentialResponse,
} from "@react-oauth/google";

interface GoogleLoginButtonProps {
  onSuccess: (response: CredentialResponse) => void;
  onError: () => void;
}

const GoogleLoginButton: React.FC<GoogleLoginButtonProps> = ({
  onSuccess,
  onError,
}) => {
  return (
    <GoogleOAuthProvider clientId="460735470135-fpmdi8monh8hph0geujm2iqp3g75kdk3.apps.googleusercontent.com">
      <GoogleLogin
        onSuccess={onSuccess}
        onError={onError}
        theme="filled_blue"
        width="210"
      />
    </GoogleOAuthProvider>
  );
};

export default GoogleLoginButton;
