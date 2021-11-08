import React from 'react';
import { ChevronUpIcon } from '@heroicons/react/solid'
import { Disclosure } from '@headlessui/react';


export const CustomDisclosure = ({title="", body=""}) => {
    return (
        <Disclosure as="div" className="mt-2">
          {({ open }) => (
            <>
              <Disclosure.Button className="flex justify-between w-full px-4 py-2 text-sm font-medium text-left 
              text-blueGray-900 bg-blueGray-100 rounded-lg 
              hover:bg-blueGray-200 focus:outline-none focus-visible:ring focus-visible:ring-purple-500 focus-visible:ring-opacity-75">
                <span>{title}</span>
                <ChevronUpIcon
                  className={`${
                    open ? 'transform rotate-180' : ''
                  } w-5 h-5 text-blueGray-500`}
                />
              </Disclosure.Button>
              <Disclosure.Panel className="px-4 pt-4 pb-2 text-sm text-gray-500">
                <div className="overflow-y-auto" dangerouslySetInnerHTML={{__html: body}}></div>
              </Disclosure.Panel>
            </>
          )}
        </Disclosure>
    )
}