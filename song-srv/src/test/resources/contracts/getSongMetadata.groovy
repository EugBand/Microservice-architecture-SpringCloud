package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        urlPath '/api/v1/songs/'
    }
    response {
        status 200
        headers {
            header('Content-Type', 'application/json')
        }
        body('''
          {
            "metadata": {
                "xmpDM:audioSampleRate": "44100",
                "channels": "2",
                "xmpDM:audioCompressor": "MP3",
                "xmpDM:audioChannelType": "Stereo",
                "version": "MPEG 3 Layer III Version 1",
                "xmpDM:duration": "3265.310302734375",
                "Content-Type": "audio/mpeg",
                "samplerate": "44100"
            }
          }
         '''
        )
    }
    request {
        method 'GET'
        urlPath '/api/v1/songs/a8995694-7a86-46d1-82b0-aab47f7b6d9e'
    }
    response {
        status 200
        headers {
            header('Content-Type', 'application/json')
        }
        body('''
          {
            "metadata": {
                "xmpDM:audioSampleRate": "44100",
                "channels": "2",
                "xmpDM:audioCompressor": "MP3",
                "xmpDM:audioChannelType": "Stereo",
                "version": "MPEG 3 Layer III Version 1",
                "xmpDM:duration": "3265.310302734375",
                "Content-Type": "audio/mpeg",
                "samplerate": "44100"
            }
          }
         '''
        )
    }
}

