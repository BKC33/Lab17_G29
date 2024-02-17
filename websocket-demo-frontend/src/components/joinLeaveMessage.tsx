import {messageType} from "../store/Slices/webSocketSlice.ts";

interface JoinLeaveMessageProps {
    sender: string;
    timestamp: string;
    messageType: messageType;
    
}
export default function JoinLeaveMessage(Props : JoinLeaveMessageProps) {
    return (
        <>
            {
                Props.messageType === messageType.JOIN ? (
                    <div className="flex items-center justify-center mb-2">
                        <div className="bg-blue-300 p-3 rounded-lg w-full text-center max-w-full">
                            <span className="font-bold">{Props.sender}</span>
                            <span> has joined the chat!</span>
                            <br/>
                            {Props.timestamp}
                        </div>
                    </div>

                ) : (
                    <div className="flex items-center justify-center mb-2">
                        <div className="bg-red-300 p-3 rounded-lg w-full text-center max-w-full">
                            <span className="font-bold">{Props.sender}</span>
                            <span> has left the chat</span>
                        </div>
                    </div>
                )
            }
        </>
    )
}